/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.shared.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

@ClassId("3ed030f6-fe67-486f-b826-ce01edb7f295")
public class IndustryICBCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 0000L;

  @Override
  protected boolean getConfiguredIsHierarchy() {
    return true;
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("IndustryICB");
  }

  @Override
  public Long getId() {
    return ID;
  }

  // code=0001 text=Oil & Gas values[x]=0001 Oil & Gas
  @Order(10)
  @ClassId("a35d0a32-472e-4e1c-9d42-109cf6e8a83b")
  public static class ICB0001 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 0001L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB0001");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=0500 text=Oil & Gas values[x]=0500 Oil & Gas
    @Order(10)
    @ClassId("e910118c-b6c2-40cd-bda3-0cd1779bf2ba")
    public static class ICB0500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 0500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB0500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=0530 text=Oil & Gas Producers values[x]=0530 Oil & Gas Producers
      @Order(10)
      @ClassId("062768a0-9916-4345-80e8-5ecdb8928ca7")
      public static class ICB0530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 0530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB0530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=0533 text=Exploration & Production values[x]=0533 Exploration & Production
        @Order(10)
        @ClassId("630848e4-63c0-4e22-90e8-2a06fcc5010d")
        public static class ICB0533 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 0533L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB0533");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=0537 text=Integrated Oil & Gas values[x]=0537 Integrated Oil & Gas
        @Order(10)
        @ClassId("354a15fc-79cb-4d3d-a754-bc36c922c2f9")
        public static class ICB0537 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 0537L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB0537");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=0570 text=Oil Equipment, Services & Distribution values[x]=0570 Oil Equipment, Services & Distribution
      @Order(10)
      @ClassId("36b1420e-d268-45a0-8876-655651ff01bf")
      public static class ICB0570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 0570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB0570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=0573 text=Oil Equipment & Services values[x]=0573 Oil Equipment & Services
        @Order(10)
        @ClassId("3c955b3d-2209-4489-a97f-765bcc56d77d")
        public static class ICB0573 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 0573L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB0573");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=0577 text=Pipelines values[x]=0577 Pipelines
        @Order(10)
        @ClassId("2a2522af-dc48-4496-9c3c-09b262c96eed")
        public static class ICB0577 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 0577L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB0577");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=1000 text=Basic Materials values[x]=1000 Basic Materials
  @Order(10)
  @ClassId("b5b3dc7d-5af1-4c8a-8f42-64941f39a460")
  public static class ICB1000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 1000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB1000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=1300 text=Chemicals values[x]=1300 Chemicals
    @Order(10)
    @ClassId("c4721ba2-8e3e-40bd-b606-0267d2c65207")
    public static class ICB1300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 1300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB1300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=1350 text=Chemicals values[x]=1350 Chemicals
      @Order(10)
      @ClassId("e3ffb435-d1c8-4944-a3ba-7f95c5c770d2")
      public static class ICB1350 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 1350L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB1350");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=1353 text=Commodity Chemicals values[x]=1353 Commodity Chemicals
        @Order(10)
        @ClassId("29edab40-0777-44fa-8355-ba983027e417")
        public static class ICB1353 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1353L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1353");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1357 text=Specialty Chemicals values[x]=1357 Specialty Chemicals
        @Order(10)
        @ClassId("ee1832f1-380b-4437-bf2a-20f01d00cdba")
        public static class ICB1357 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1357L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1357");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=1700 text=Basic Resources values[x]=1700 Basic Resources
    @Order(10)
    @ClassId("c2dd2d82-41b2-4747-8b4a-e30f0bc16e39")
    public static class ICB1700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 1700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB1700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=1730 text=Forestry & Paper values[x]=1730 Forestry & Paper
      @Order(10)
      @ClassId("b3de9a8b-3770-4eff-b729-b1924dcf8f2c")
      public static class ICB1730 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 1730L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB1730");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=1733 text=Forestry values[x]=1733 Forestry
        @Order(10)
        @ClassId("ec4c6c61-0c2e-4fc9-9c08-7ed175f6d84f")
        public static class ICB1733 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1733L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1733");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1737 text=Paper values[x]=1737 Paper
        @Order(10)
        @ClassId("4397e661-18e6-4b16-b4a0-706c5a3ec719")
        public static class ICB1737 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1737L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1737");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=1750 text=Industrial Metals values[x]=1750 Industrial Metals
      @Order(10)
      @ClassId("75ba23bb-cbb5-45f5-818e-e44578ae467c")
      public static class ICB1750 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 1750L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB1750");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=1753 text=Aluminum values[x]=1753 Aluminum
        @Order(10)
        @ClassId("513def7a-c1db-49d8-a619-38e5f5dd0bde")
        public static class ICB1753 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1753L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1753");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1755 text=Nonferrous Metals values[x]=1755 Nonferrous Metals
        @Order(10)
        @ClassId("c465a4a4-8e22-49ad-a1eb-b2a9fca448fc")
        public static class ICB1755 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1755L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1755");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1757 text=Steel values[x]=1757 Steel
        @Order(10)
        @ClassId("1f0e13fe-daee-44b7-bcf1-975c36cb0495")
        public static class ICB1757 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1757L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1757");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=1770 text=Mining values[x]=1770 Mining
      @Order(10)
      @ClassId("e8527c99-bb41-4069-8ee2-dc9d9ef4dde4")
      public static class ICB1770 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 1770L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB1770");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=1771 text=Coal values[x]=1771 Coal
        @Order(10)
        @ClassId("c79a214a-8cea-4b16-9754-8431992e94f8")
        public static class ICB1771 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1771L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1771");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1773 text=Diamonds & Gemstones values[x]=1773 Diamonds & Gemstones
        @Order(10)
        @ClassId("70c59ae6-32fb-4b0a-8156-0c1e288f91e4")
        public static class ICB1773 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1773L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1773");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1775 text=General Mining values[x]=1775 General Mining
        @Order(10)
        @ClassId("adc04dcd-0ba3-41e5-9311-6ddf8820dc68")
        public static class ICB1775 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1775L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1775");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1777 text=Gold Mining values[x]=1777 Gold Mining
        @Order(10)
        @ClassId("0b3c2180-43f4-40c5-b16d-dc9b94da7857")
        public static class ICB1777 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1777L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1777");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=1779 text=Platinum & Precious Metals values[x]=1779 Platinum & Precious Metals
        @Order(10)
        @ClassId("66eac2d5-f09d-42e6-bc16-5a8d8c97bb2e")
        public static class ICB1779 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 1779L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB1779");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=2000 text=Industrials values[x]=2000 Industrials
  @Order(10)
  @ClassId("19e5088c-b81b-4f1c-8956-6f71e698a06a")
  public static class ICB2000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 2000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB2000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=2300 text=Construction & Materials values[x]=2300 Construction & Materials
    @Order(10)
    @ClassId("7a84f620-701f-45d1-9de0-eb82447ad261")
    public static class ICB2300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 2300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB2300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=2350 text=Construction & Materials values[x]=2350 Construction & Materials
      @Order(10)
      @ClassId("ed783a70-2993-455b-9952-3d41ec5c7453")
      public static class ICB2350 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2350L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2350");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2353 text=Building Materials & Fixtures values[x]=2353 Building Materials & Fixtures
        @Order(10)
        @ClassId("f9adba81-4b30-452d-962b-06570d82ef1d")
        public static class ICB2353 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2353L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2353");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2357 text=Heavy Construction values[x]=2357 Heavy Construction
        @Order(10)
        @ClassId("89473dd6-8a8d-438c-86cf-a21d0523040a")
        public static class ICB2357 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2357L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2357");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=2700 text=Industrial Goods & Services values[x]=2700 Industrial Goods & Services
    @Order(10)
    @ClassId("9c484cd7-8ec3-451b-81c5-314ab751417e")
    public static class ICB2700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 2700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB2700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=2710 text=Aerospace & Defense values[x]=2710 Aerospace & Defense
      @Order(10)
      @ClassId("474cd00c-9ae1-4733-a0c8-cad995832515")
      public static class ICB2710 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2710L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2710");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2713 text=Aerospace values[x]=2713 Aerospace
        @Order(10)
        @ClassId("d5ffc23f-079b-4af8-a17a-c76d356df6f7")
        public static class ICB2713 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2713L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2713");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2717 text=Defense values[x]=2717 Defense
        @Order(10)
        @ClassId("3524fb1b-b347-402d-9ff0-b8182a5e562e")
        public static class ICB2717 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2717L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2717");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2720 text=General Industrials values[x]=2720 General Industrials
      @Order(10)
      @ClassId("367b97ad-a27f-4838-83dc-bedc64d39ce4")
      public static class ICB2720 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2720L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2720");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2723 text=Containers & Packaging values[x]=2723 Containers & Packaging
        @Order(10)
        @ClassId("8dd43cc0-2c6e-4a3b-848c-c8ac9a744452")
        public static class ICB2723 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2723L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2723");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2727 text=Diversified Industrials values[x]=2727 Diversified Industrials
        @Order(10)
        @ClassId("5262faa6-07d6-4c97-b64f-49252f4d3437")
        public static class ICB2727 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2727L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2727");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2730 text=Electronic & Electrical Equipment values[x]=2730 Electronic & Electrical Equipment
      @Order(10)
      @ClassId("b1192c1a-d910-4bde-ab1e-8b96547fd041")
      public static class ICB2730 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2730L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2730");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2733 text=Electrical Components & Equipment values[x]=2733 Electrical Components & Equipment
        @Order(10)
        @ClassId("387d5174-e673-41f6-bd48-7f0371f7fcbe")
        public static class ICB2733 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2733L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2733");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2737 text=Electronic Equipment values[x]=2737 Electronic Equipment
        @Order(10)
        @ClassId("3c605517-d377-41f2-88d1-84654afda4b9")
        public static class ICB2737 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2737L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2737");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2750 text=Industrial Engineering values[x]=2750 Industrial Engineering
      @Order(10)
      @ClassId("84f2ca3a-261b-4494-aa32-d9b866c23685")
      public static class ICB2750 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2750L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2750");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2753 text=Commercial Vehicles & Trucks values[x]=2753 Commercial Vehicles & Trucks
        @Order(10)
        @ClassId("10e0162b-a8a8-4d69-a359-5a30d3a078b3")
        public static class ICB2753 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2753L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2753");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2757 text=Industrial Machinery values[x]=2757 Industrial Machinery
        @Order(10)
        @ClassId("6d2583f9-9d29-42b2-98cb-bae505d2a70f")
        public static class ICB2757 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2757L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2757");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2770 text=Industrial Transportation values[x]=2770 Industrial Transportation
      @Order(10)
      @ClassId("432a787f-357d-4f7a-904f-410ab9f9cdfb")
      public static class ICB2770 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2770L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2770");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2771 text=Delivery Services values[x]=2771 Delivery Services
        @Order(10)
        @ClassId("3655a48a-ccc2-4fb8-ba0b-6d9ac2ca7df9")
        public static class ICB2771 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2771L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2771");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2773 text=Marine Transportation values[x]=2773 Marine Transportation
        @Order(10)
        @ClassId("8f54622d-14a3-4640-a306-bf3b09bceab8")
        public static class ICB2773 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2773L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2773");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2775 text=Railroads values[x]=2775 Railroads
        @Order(10)
        @ClassId("5df29925-7200-4dd1-9568-1f7fb7052b59")
        public static class ICB2775 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2775L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2775");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2777 text=Transportation Services values[x]=2777 Transportation Services
        @Order(10)
        @ClassId("8e8be201-ed22-4dc6-b880-9ac42f3871cb")
        public static class ICB2777 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2777L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2777");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2779 text=Trucking values[x]=2779 Trucking
        @Order(10)
        @ClassId("1ac3e32b-1a41-4d9c-b00d-21ef3148d0aa")
        public static class ICB2779 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2779L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2779");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=2790 text=Support Services values[x]=2790 Support Services
      @Order(10)
      @ClassId("5617fced-afcb-43bc-af7e-482651638d54")
      public static class ICB2790 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 2790L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB2790");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=2791 text=Business Support Services values[x]=2791 Business Support Services
        @Order(10)
        @ClassId("668bb1b3-799d-4b1c-bfd0-8375c9dad699")
        public static class ICB2791 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2791L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2791");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2793 text=Business Training & Employment Agencies values[x]=2793 Business Training & Employment Agencies
        @Order(10)
        @ClassId("fdc0d60f-a2db-4c00-96f3-791a8c3a9538")
        public static class ICB2793 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2793L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2793");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2795 text=Financial Administration values[x]=2795 Financial Administration
        @Order(10)
        @ClassId("6a27e402-f531-4cda-b73a-2309bde9f4e2")
        public static class ICB2795 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2795L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2795");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2797 text=Industrial Suppliers values[x]=2797 Industrial Suppliers
        @Order(10)
        @ClassId("b41ab1e8-8e33-4820-8842-da646261a4e5")
        public static class ICB2797 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2797L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2797");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=2799 text=Waste & Disposal Services values[x]=2799 Waste & Disposal Services
        @Order(10)
        @ClassId("3f55df55-ccce-4ede-9303-a6858ac1fbc6")
        public static class ICB2799 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 2799L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB2799");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=3000 text=Consumer Goods values[x]=3000 Consumer Goods
  @Order(10)
  @ClassId("ebaf70c2-ca18-4c06-acfd-cfdab9389722")
  public static class ICB3000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 3000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB3000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=3300 text=Automobiles & Parts values[x]=3300 Automobiles & Parts
    @Order(10)
    @ClassId("a1b38138-6edf-40d6-a62f-9e47df9ab8ea")
    public static class ICB3300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 3300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB3300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=3350 text=Automobiles & Parts values[x]=3350 Automobiles & Parts
      @Order(10)
      @ClassId("07de9b46-fcd5-40c0-8420-7ced4096338a")
      public static class ICB3350 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3350L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3350");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3353 text=Automobiles values[x]=3353 Automobiles
        @Order(10)
        @ClassId("7edbd921-d518-4db6-b14c-79df4d8dc008")
        public static class ICB3353 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3353L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3353");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3355 text=Auto Parts values[x]=3355 Auto Parts
        @Order(10)
        @ClassId("f8d02c38-9eb4-4ea8-85f3-d858fc402c15")
        public static class ICB3355 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3355L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3355");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3357 text=Tires values[x]=3357 Tires
        @Order(10)
        @ClassId("0dbcb688-2e37-4283-8d4b-03f924894fc4")
        public static class ICB3357 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3357L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3357");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=3500 text=Food & Beverage values[x]=3500 Food & Beverage
    @Order(10)
    @ClassId("c4f510fa-c518-4156-94ca-a4db4a17664e")
    public static class ICB3500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 3500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB3500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=3530 text=Beverages values[x]=3530 Beverages
      @Order(10)
      @ClassId("1570fa57-8708-46e1-9795-79f4c119dca0")
      public static class ICB3530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3533 text=Brewers values[x]=3533 Brewers
        @Order(10)
        @ClassId("a83e3523-2303-462b-806e-4df264adb037")
        public static class ICB3533 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3533L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3533");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3535 text=Distillers & Vintners values[x]=3535 Distillers & Vintners
        @Order(10)
        @ClassId("5bc7dbad-7ff3-4f9d-a07a-7bad81d713fd")
        public static class ICB3535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3537 text=Soft Drinks values[x]=3537 Soft Drinks
        @Order(10)
        @ClassId("0ba4f530-d4d1-463a-b345-fd3941b7b365")
        public static class ICB3537 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3537L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3537");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=3570 text=Food Producers values[x]=3570 Food Producers
      @Order(10)
      @ClassId("572facbc-554d-459e-9e7a-34e9cff139df")
      public static class ICB3570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3573 text=Farming & Fishing values[x]=3573 Farming & Fishing
        @Order(10)
        @ClassId("b70fe9a5-4615-4a12-9100-9dfccf0872a1")
        public static class ICB3573 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3573L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3573");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3577 text=Food Products values[x]=3577 Food Products
        @Order(10)
        @ClassId("3d9b757f-09e2-44f7-ab5b-01b761540c11")
        public static class ICB3577 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3577L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3577");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=3700 text=Personal & Household Goods values[x]=3700 Personal & Household Goods
    @Order(10)
    @ClassId("ed7d3051-6faa-4010-b83d-a72aeb5d5b78")
    public static class ICB3700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 3700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB3700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=3720 text=Household Goods values[x]=3720 Household Goods
      @Order(10)
      @ClassId("2fa81d37-3f3e-4083-acd7-7b22d28954a7")
      public static class ICB3720 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3720L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3720");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3722 text=Durable Household Products values[x]=3722 Durable Household Products
        @Order(10)
        @ClassId("63d2eff8-806e-4f9a-91cf-7ed076241e57")
        public static class ICB3722 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3722L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3722");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3724 text=Nondurable Household Products values[x]=3724 Nondurable Household Products
        @Order(10)
        @ClassId("a600c9d2-eec2-44ea-b648-64b847ca5835")
        public static class ICB3724 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3724L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3724");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3726 text=Furnishings values[x]=3726 Furnishings
        @Order(10)
        @ClassId("ae6a4697-de17-4850-8829-39cbd1cdce3f")
        public static class ICB3726 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3726L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3726");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3728 text=Home Construction values[x]=3728 Home Construction
        @Order(10)
        @ClassId("470f6dca-5cbf-4015-9a3b-c8bb8502a18a")
        public static class ICB3728 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3728L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3728");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=3740 text=Leisure Goods values[x]=3740 Leisure Goods
      @Order(10)
      @ClassId("265ce579-ade8-47c2-b075-e776c69e9ca3")
      public static class ICB3740 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3740L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3740");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3743 text=Consumer Electronics values[x]=3743 Consumer Electronics
        @Order(10)
        @ClassId("0d2ea4ff-8fcc-4b3e-ac9b-e02169db45df")
        public static class ICB3743 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3743L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3743");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3745 text=Recreational Products values[x]=3745 Recreational Products
        @Order(10)
        @ClassId("d1cec06d-405b-46d7-b1e8-5d53811ce26e")
        public static class ICB3745 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3745L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3745");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3747 text=Toys values[x]=3747 Toys
        @Order(10)
        @ClassId("c384ea6b-f4c0-4a30-9745-b55678147325")
        public static class ICB3747 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3747L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3747");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=3760 text=Personal Goods values[x]=3760 Personal Goods
      @Order(10)
      @ClassId("23dbeffe-b302-4cbd-9af8-7e5a46f20936")
      public static class ICB3760 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3760L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3760");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3763 text=Clothing & Accessories values[x]=3763 Clothing & Accessories
        @Order(10)
        @ClassId("752a9b09-59c5-4e97-a190-10a88ba3f8a7")
        public static class ICB3763 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3763L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3763");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3765 text=Footwear values[x]=3765 Footwear
        @Order(10)
        @ClassId("475b1365-757a-4ee7-9b5b-7325c9e9d17e")
        public static class ICB3765 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3765L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3765");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=3767 text=Personal Products values[x]=3767 Personal Products
        @Order(10)
        @ClassId("b1a2e328-2b00-430f-b917-eb63632e04d3")
        public static class ICB3767 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3767L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3767");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=3780 text=Tobacco values[x]=3780 Tobacco
      @Order(10)
      @ClassId("cfb31054-1e0b-45a9-a9f2-e895b1de703e")
      public static class ICB3780 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 3780L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB3780");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=3785 text=Tobacco values[x]=3785 Tobacco
        @Order(10)
        @ClassId("82d985dc-f486-4603-987b-00fbc7a85f4e")
        public static class ICB3785 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 3785L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB3785");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=4000 text=Health Care values[x]=4000 Health Care
  @Order(10)
  @ClassId("ab9d2c9e-e221-4c5f-9f2e-b2ea6ee551df")
  public static class ICB4000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 4000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB4000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=4500 text=Health Care values[x]=4500 Health Care
    @Order(10)
    @ClassId("c499ec96-b2a7-4394-9af1-4ceaad373566")
    public static class ICB4500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 4500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB4500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=4530 text=Health Care Equipment & Services values[x]=4530 Health Care Equipment & Services
      @Order(10)
      @ClassId("053f8db3-936a-46ff-8374-555de6131bac")
      public static class ICB4530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 4530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB4530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=4533 text=Health Care Providers values[x]=4533 Health Care Providers
        @Order(10)
        @ClassId("b2fa4b94-207e-4764-930c-2b47f6ab8cb6")
        public static class ICB4533 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4533L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4533");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=4535 text=Medical Equipment values[x]=4535 Medical Equipment
        @Order(10)
        @ClassId("60b3fd65-5b2f-4a35-9bac-0b7334b7b49c")
        public static class ICB4535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=4537 text=Medical Supplies values[x]=4537 Medical Supplies
        @Order(10)
        @ClassId("1cfb1c8f-35d8-4ff8-8d2e-6c994127944e")
        public static class ICB4537 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4537L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4537");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=4570 text=Pharmaceuticals & Biotechnology values[x]=4570 Pharmaceuticals & Biotechnology
      @Order(10)
      @ClassId("72cd6320-14f7-40b5-97e2-3bc4738ae776")
      public static class ICB4570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 4570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB4570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=4573 text=Biotechnology values[x]=4573 Biotechnology
        @Order(10)
        @ClassId("c46eb60f-de38-4c5a-be43-3f55d85eec0f")
        public static class ICB4573 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4573L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4573");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=4577 text=Pharmaceuticals values[x]=4577 Pharmaceuticals
        @Order(10)
        @ClassId("12a1bdff-014a-4148-aa13-72e0b747c913")
        public static class ICB4577 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 4577L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB4577");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=5000 text=Consumer Services values[x]=5000 Consumer Services
  @Order(10)
  @ClassId("59618d0e-6a9f-452f-a3af-4f54525cec8c")
  public static class ICB5000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 5000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB5000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=5300 text=Retail values[x]=5300 Retail
    @Order(10)
    @ClassId("3321c399-4c81-418f-978d-61749aa581bf")
    public static class ICB5300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 5300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB5300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=5330 text=Food & Drug Retailers values[x]=5330 Food & Drug Retailers
      @Order(10)
      @ClassId("55fd94d8-0bea-472b-b825-d3ae3e95e28b")
      public static class ICB5330 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 5330L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB5330");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=5333 text=Drug Retailers values[x]=5333 Drug Retailers
        @Order(10)
        @ClassId("59af65ec-ad37-43a8-a34e-20ed6ef207f9")
        public static class ICB5333 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5333L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5333");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5337 text=Food Retailers & Wholesalers values[x]=5337 Food Retailers & Wholesalers
        @Order(10)
        @ClassId("f9b14ad4-8873-4566-b9dc-f8947be3608c")
        public static class ICB5337 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5337L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5337");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=5370 text=General Retailers values[x]=5370 General Retailers
      @Order(10)
      @ClassId("120400ef-a205-43a0-b9cc-8470c8aa0e37")
      public static class ICB5370 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 5370L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB5370");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=5371 text=Apparel Retailers values[x]=5371 Apparel Retailers
        @Order(10)
        @ClassId("92a76eef-cfe9-43ad-ac19-9595d307f761")
        public static class ICB5371 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5371L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5371");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5373 text=Broadline Retailers values[x]=5373 Broadline Retailers
        @Order(10)
        @ClassId("2eb45d3f-d080-454b-8e74-e6033258cce8")
        public static class ICB5373 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5373L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5373");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5375 text=Home Improvement Retailers values[x]=5375 Home Improvement Retailers
        @Order(10)
        @ClassId("13adb4e3-dd4f-4659-83a7-9436efe2bf18")
        public static class ICB5375 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5375L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5375");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5377 text=Specialized Consumer Services values[x]=5377 Specialized Consumer Services
        @Order(10)
        @ClassId("7ed2db7f-7f15-46e1-bbe1-e3184866602c")
        public static class ICB5377 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5377L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5377");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5379 text=Specialty Retailers values[x]=5379 Specialty Retailers
        @Order(10)
        @ClassId("b5f2746e-d708-430b-afbc-db591060a79c")
        public static class ICB5379 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5379L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5379");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=5500 text=Media values[x]=5500 Media
    @Order(10)
    @ClassId("8e280b40-21e7-4021-ba28-612ba3c7b37a")
    public static class ICB5500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 5500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB5500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=5550 text=Media values[x]=5550 Media
      @Order(10)
      @ClassId("6b8e79ad-e4ef-4890-9ea0-c7e1e9a881f2")
      public static class ICB5550 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 5550L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB5550");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=5553 text=Broadcasting & Entertainment values[x]=5553 Broadcasting & Entertainment
        @Order(10)
        @ClassId("7cd38de3-9e20-4864-947b-d03214eaec52")
        public static class ICB5553 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5553L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5553");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5555 text=Media Agencies values[x]=5555 Media Agencies
        @Order(10)
        @ClassId("c3e3680f-d6be-4ccb-b873-2752d57e3150")
        public static class ICB5555 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5555L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5555");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5557 text=Publishing values[x]=5557 Publishing
        @Order(10)
        @ClassId("0489ee6d-e1c8-4609-9f05-6b6a47507241")
        public static class ICB5557 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5557L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5557");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=5700 text=Travel & Leisure values[x]=5700 Travel & Leisure
    @Order(10)
    @ClassId("9b6293cd-76d8-4f73-9d07-4b983816c8cd")
    public static class ICB5700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 5700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB5700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=5750 text=Travel & Leisure values[x]=5750 Travel & Leisure
      @Order(10)
      @ClassId("53d014e1-9590-40e9-9de7-d4d0752844d4")
      public static class ICB5750 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 5750L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB5750");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=5751 text=Airlines values[x]=5751 Airlines
        @Order(10)
        @ClassId("5e5747f6-b891-489b-9b9e-5b30618527db")
        public static class ICB5751 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5751L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5751");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5752 text=Gambling values[x]=5752 Gambling
        @Order(10)
        @ClassId("54b5cf1b-2462-4465-aabb-c886d487a387")
        public static class ICB5752 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5752L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5752");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5753 text=Hotels values[x]=5753 Hotels
        @Order(10)
        @ClassId("0cda87bb-d381-4b2c-8443-8c5f30f9b875")
        public static class ICB5753 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5753L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5753");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5755 text=Recreational Services values[x]=5755 Recreational Services
        @Order(10)
        @ClassId("917351bb-0dfe-48b9-a500-c389c5b4289d")
        public static class ICB5755 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5755L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5755");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5757 text=Restaurants & Bars values[x]=5757 Restaurants & Bars
        @Order(10)
        @ClassId("06a47ba0-da6f-445c-8920-4d243effa515")
        public static class ICB5757 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5757L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5757");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=5759 text=Travel & Tourism values[x]=5759 Travel & Tourism
        @Order(10)
        @ClassId("e76761bb-e991-474f-95cb-74a9c5b7e591")
        public static class ICB5759 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 5759L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB5759");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=6000 text=Telecommunications values[x]=6000 Telecommunications
  @Order(10)
  @ClassId("d678a779-9679-43a3-9745-179e8347bb19")
  public static class ICB6000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 6000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB6000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=6500 text=Telecommunications values[x]=6500 Telecommunications
    @Order(10)
    @ClassId("a95e45ee-01d2-47d0-a6ad-c37c9e5a29f3")
    public static class ICB6500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 6500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB6500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=6530 text=Fixed Line Telecommunications values[x]=6530 Fixed Line Telecommunications
      @Order(10)
      @ClassId("7aeff38d-ef46-49fc-9c67-9b8047639924")
      public static class ICB6530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 6530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB6530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=6535 text=Fixed Line Telecommunications values[x]=6535 Fixed Line Telecommunications
        @Order(10)
        @ClassId("4650ad1a-5e70-45fd-9063-ff78c991df72")
        public static class ICB6535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 6535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB6535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=6570 text=Mobile Telecommunications values[x]=6570 Mobile Telecommunications
      @Order(10)
      @ClassId("017f2f2b-adee-4042-9a88-c05720925b04")
      public static class ICB6570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 6570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB6570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=6575 text=Mobile Telecommunications values[x]=6575 Mobile Telecommunications
        @Order(10)
        @ClassId("ecdb3fb1-8647-465f-b3a7-acd8dcfede2e")
        public static class ICB6575 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 6575L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB6575");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=7000 text=Utilities values[x]=7000 Utilities
  @Order(10)
  @ClassId("98b5d842-16c0-4ea7-879a-b82d8b1f7966")
  public static class ICB7000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 7000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB7000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=7500 text=Utilities values[x]=7500 Utilities
    @Order(10)
    @ClassId("a3845844-43ea-4dfa-a89e-b0a9f290b31f")
    public static class ICB7500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 7500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB7500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=7530 text=Electricity values[x]=7530 Electricity
      @Order(10)
      @ClassId("a0f6b1bf-d641-45dc-a7a3-ba13e5efeb68")
      public static class ICB7530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 7530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB7530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=7535 text=Electricity values[x]=7535 Electricity
        @Order(10)
        @ClassId("98ae16db-f81e-4495-a678-0f2176f34758")
        public static class ICB7535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 7535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB7535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=7570 text=Gas, Water & Multiutilities values[x]=7570 Gas, Water & Multiutilities
      @Order(10)
      @ClassId("60fe6e28-4df1-4827-a7de-d64a5f959465")
      public static class ICB7570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 7570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB7570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=7573 text=Gas Distribution values[x]=7573 Gas Distribution
        @Order(10)
        @ClassId("cdee04f1-5f74-49bb-bccf-467d98c1f55d")
        public static class ICB7573 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 7573L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB7573");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=7575 text=Multiutilities values[x]=7575 Multiutilities
        @Order(10)
        @ClassId("94cf0a59-42dc-4017-90a1-77006b3a7a87")
        public static class ICB7575 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 7575L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB7575");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=7577 text=Water values[x]=7577 Water
        @Order(10)
        @ClassId("e81296c1-a62d-44d5-b0ef-e9e16cc20ccd")
        public static class ICB7577 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 7577L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB7577");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=8000 text=Financials values[x]=8000 Financials
  @Order(10)
  @ClassId("2b59d80b-5cda-4741-8c63-071bd80c51b3")
  public static class ICB8000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 8000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB8000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=8300 text=Banks values[x]=8300 Banks
    @Order(10)
    @ClassId("d90167e9-1485-420e-b47c-b61f603303ee")
    public static class ICB8300 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 8300L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB8300");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=8350 text=Banks values[x]=8350 Banks
      @Order(10)
      @ClassId("a6d9208a-a977-4528-85a6-4db01fc682e1")
      public static class ICB8350 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8350L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8350");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8355 text=Banks values[x]=8355 Banks
        @Order(10)
        @ClassId("536b8090-7542-4187-a38d-77778732f884")
        public static class ICB8355 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8355L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8355");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=8500 text=Insurance values[x]=8500 Insurance
    @Order(10)
    @ClassId("acf1756d-3241-4059-b9eb-de3897962ba5")
    public static class ICB8500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 8500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB8500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=8530 text=Nonlife Insurance values[x]=8530 Nonlife Insurance
      @Order(10)
      @ClassId("ac9d74b7-9621-4b5c-a053-71cff549f5e9")
      public static class ICB8530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8532 text=Full Line Insurance values[x]=8532 Full Line Insurance
        @Order(10)
        @ClassId("183b0dbd-680c-4f23-9929-fe4d5e95383f")
        public static class ICB8532 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8532L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8532");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8534 text=Insurance Brokers values[x]=8534 Insurance Brokers
        @Order(10)
        @ClassId("88c3ad48-1008-4026-8f82-77b89eb17a9a")
        public static class ICB8534 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8534L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8534");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8536 text=Property & Casualty Insurance values[x]=8536 Property & Casualty Insurance
        @Order(10)
        @ClassId("5a434d43-7f32-4a65-bd42-aaaa272a5962")
        public static class ICB8536 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8536L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8536");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8538 text=Reinsurance values[x]=8538 Reinsurance
        @Order(10)
        @ClassId("6603737b-8cef-448d-94be-182f54a993ee")
        public static class ICB8538 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8538L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8538");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=8570 text=Life Insurance values[x]=8570 Life Insurance
      @Order(10)
      @ClassId("352dd937-35e6-4a57-8f5d-fb5607383b7f")
      public static class ICB8570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8575 text=Life Insurance values[x]=8575 Life Insurance
        @Order(10)
        @ClassId("0ff401a5-c342-4307-b06a-3e7b05dad668")
        public static class ICB8575 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8575L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8575");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }

    // code=8700 text=Financial Services values[x]=8700 Financial Services
    @Order(10)
    @ClassId("817385ac-7417-4514-8d03-b271460b3713")
    public static class ICB8700 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 8700L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB8700");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=8730 text=Real Estate values[x]=8730 Real Estate
      @Order(10)
      @ClassId("04b55204-97cc-4f5d-8882-e591292f2c55")
      public static class ICB8730 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8730L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8730");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8733 text=Real Estate Holding & Development values[x]=8733 Real Estate Holding & Development
        @Order(10)
        @ClassId("53076f7f-623e-43b9-a8b0-55b8492606b9")
        public static class ICB8733 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8733L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8733");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8737 text=Real Estate Investment Trusts values[x]=8737 Real Estate Investment Trusts
        @Order(10)
        @ClassId("589a25f7-4067-4a3d-8884-371600810131")
        public static class ICB8737 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8737L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8737");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=8770 text=General Financial values[x]=8770 General Financial
      @Order(10)
      @ClassId("8c077a7b-4cf5-4fd5-be9f-8001e54875cd")
      public static class ICB8770 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8770L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8770");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8771 text=Asset Managers values[x]=8771 Asset Managers
        @Order(10)
        @ClassId("537fbd7d-ddd7-46dd-abc2-03abfddc27c5")
        public static class ICB8771 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8771L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8771");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8773 text=Consumer Finance values[x]=8773 Consumer Finance
        @Order(10)
        @ClassId("7a91eaff-1fea-4e52-9ddd-dbfa52a1dedc")
        public static class ICB8773 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8773L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8773");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8775 text=Specialty Finance values[x]=8775 Specialty Finance
        @Order(10)
        @ClassId("c15d83d5-525b-49c9-8f88-6f3a50813bd7")
        public static class ICB8775 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8775L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8775");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8777 text=Investment Services values[x]=8777 Investment Services
        @Order(10)
        @ClassId("ebd8ea07-f507-41b5-8c33-a83bf5846437")
        public static class ICB8777 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8777L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8777");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=8779 text=Mortgage Finance values[x]=8779 Mortgage Finance
        @Order(10)
        @ClassId("d5a5ec85-737f-4f1b-8ba9-68a81332a644")
        public static class ICB8779 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8779L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8779");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=8980 text=Equity Investment Instruments values[x]=8980 Equity Investment Instruments
      @Order(10)
      @ClassId("fd79f173-0d52-482c-905b-9a7bfbef9cf9")
      public static class ICB8980 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8980L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8980");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8985 text=Equity Investment Instruments values[x]=8985 Equity Investment Instruments
        @Order(10)
        @ClassId("4fd81082-99e7-4e9b-bd8d-298f096bcc6a")
        public static class ICB8985 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8985L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8985");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=8990 text=Nonequity Investment Instruments values[x]=8990 Nonequity Investment Instruments
      @Order(10)
      @ClassId("0a686d12-490d-415b-9f0e-f2ba7796687a")
      public static class ICB8990 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 8990L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB8990");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=8995 text=Nonequity Investment Instruments values[x]=8995 Nonequity Investment Instruments
        @Order(10)
        @ClassId("9d52b9ab-db4b-4f88-9145-79546d90eb94")
        public static class ICB8995 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 8995L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB8995");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }

  // code=9000 text=Technology values[x]=9000 Technology
  @Order(10)
  @ClassId("482d2a57-df3d-4f4a-8cc8-41f9b5a4168b")
  public static class ICB9000 extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final Long ID = 9000L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ICB9000");
    }

    @Override
    public Long getId() {
      return ID;
    }

    // code=9500 text=Technology values[x]=9500 Technology
    @Order(10)
    @ClassId("02b1f850-17ff-411a-943a-fd5ec641f23f")
    public static class ICB9500 extends AbstractCode<Long> {
      private static final long serialVersionUID = 1L;
      public static final Long ID = 9500L;

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ICB9500");
      }

      @Override
      public Long getId() {
        return ID;
      }

      // code=9530 text=Software & Computer Services values[x]=9530 Software & Computer Services
      @Order(10)
      @ClassId("501a0b93-76ea-47a3-9e15-2513e6291b63")
      public static class ICB9530 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 9530L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB9530");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=9533 text=Computer Services values[x]=9533 Computer Services
        @Order(10)
        @ClassId("b69370b3-b401-4a73-961c-1c16f45987de")
        public static class ICB9533 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9533L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9533");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9535 text=Internet values[x]=9535 Internet
        @Order(10)
        @ClassId("cb8db925-78ff-41bb-97f4-d57c82ceffec")
        public static class ICB9535 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9535L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9535");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9537 text=Software values[x]=9537 Software
        @Order(10)
        @ClassId("11a7cf7b-db06-46b3-a119-d25e406a9e99")
        public static class ICB9537 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9537L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9537");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }

      // code=9570 text=Technology Hardware & Equipment values[x]=9570 Technology Hardware & Equipment
      @Order(10)
      @ClassId("c32701bd-c822-4633-856e-994b1e39cca2")
      public static class ICB9570 extends AbstractCode<Long> {
        private static final long serialVersionUID = 1L;
        public static final Long ID = 9570L;

        @Override
        protected String getConfiguredText() {
          return TEXTS.get("ICB9570");
        }

        @Override
        public Long getId() {
          return ID;
        }

        // code=9572 text=Computer Hardware values[x]=9572 Computer Hardware
        @Order(10)
        @ClassId("7997aae9-32ea-40db-b726-1a86e08536a0")
        public static class ICB9572 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9572L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9572");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9574 text=Electronic Office Equipment values[x]=9574 Electronic Office Equipment
        @Order(10)
        @ClassId("84a71759-848a-4fb9-bd4d-dcd4443093e5")
        public static class ICB9574 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9574L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9574");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9576 text=Semiconductors values[x]=9576 Semiconductors
        @Order(10)
        @ClassId("dcc3dabf-abe8-4950-8368-2acb1a0b53bd")
        public static class ICB9576 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9576L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9576");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }

        // code=9578 text=Telecommunications Equipment values[x]=9578 Telecommunications Equipment
        @Order(10)
        @ClassId("8fb4d503-83ad-48d8-96cb-60b339636e85")
        public static class ICB9578 extends AbstractCode<Long> {
          private static final long serialVersionUID = 1L;
          public static final Long ID = 9578L;

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("ICB9578");
          }

          @Override
          public Long getId() {
            return ID;
          }
        }
      }
    }
  }
}
